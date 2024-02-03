# Temas técnicos desse projeto

- [Annotations](#annotations)
- Ports and adapters
- DDD

## Annotations

**Annotations** fazem parte do dia a dia do desenvolvimento de software, isso porque precisamos, muitas vezes,
encapsular dados e reutilizar funcionalidades em boa parte do código. Acontece que nem todo desenvolvedor chegou a fazer
uma annotation do zero. Por mais que ela seja super comum de ser utilizada, a criação do zero é um pouco mais rara de ser
vista dado o fato de que já temos muitas annotations utilitárias nativas ou que vêm em alguma biblioteca externa.

### O que são annotations

Annotations são funções que podem ser usadas em classes, métodos ou atributos.
As annotations têm a seguinte assinatura: **@** seguido do **nome da anotação**.

### Criação de annotations (Kotlin)

Iremos criar uma annotation que mascara dados sensíveis quando o atributo de uma classe conter a annotation nomeada como
**Mask** em cima de sua declaração.

```kotlin
/*
Precisamos importar do kotlin.reflect o atributo declaredMemberProperties e o método findAnnotation que serão
utilizados adiante
*/

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

// Aqui utilizamos uma annotation chamada @Retention com o argumento RUNTIME indicando que rodará em tempo de execução
@Retention(AnnotationRetention.RUNTIME)
// Aqui usamos a estrutura que o Kotlin pede para declarar a annotation e seu nome representativo
annotation class Mask
```

Agora precisamos fazer alguma lógica para que quando alguma propriedade chame essa annotation, ela possa realizar um
comportamento de mascarar dados.

```kotlin
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Mask

/*
Aqui criamos um método que ficará responsável por receber qualquer objeto e nele percorrer suas propriedades para mascarar
quem usar a annotation Mask
*/
fun maskValue(obj: Any) {
    /*  Aqui conseguimos pegar do objeto as propriedades de todos os membros declarados nele.
     Ex de obj::class.declaredMemberProperties: [val domain.client.Client.address: domain.client.Client.Address, val domain.client.Client.cpf: kotlin.String]
    e, se
    */
    obj::class.declaredMemberProperties
        .filter {
            it.findAnnotation<Mask>() != null
        }.associateWith { property ->  property.findAnnotation<Mask>() } /* aqui estamos pegando a propriedade que possuem a annotation Mask em si
         para manipularmos seus valores futuramente */
        // Para conseguirmos tratar cada propriedade individualmente, precisamos percorrer esse array de propriedades.
        .forEach { (property) ->
            // Com a propriedade em nossas mãos, agora conseguimos saber qual campo estamos nesse exato momento do loop
            val field = obj::class.java.getDeclaredField(property.name)

            /* Aqui usaremos uma funcionalidade bastante conhecida no Java que é a reflection, com ela poderemos mudar a acessibilidade
             do campo, fazendo com que possamos modificar seu valor em seguida.
            */
            field.isAccessible = true

            /* Como estamos lidando com uma linguagem tipada, precisamos identificar qual é o tipo do valor do campo atual
             aqui estamos verificando apenas 3 tipos primitivos sendo: String, Double, Int.
             Outro ponto é que existe a possibilidade de não ser um tipo primitivo e sim um objeto, então no else criamos uma recursão para que
             possamos enviar novamente o valor do campo para refazer toda essa etapa de loop nas propriedades desse objeto.
            */

            field.get(obj).let {
                if(it != null) when(field.type) {
                    String::class.java -> field.set(obj, "*".repeat((it as String).length))
                    Int::class.java -> field.set(obj, 0)
                    Double::class.java -> field.set(obj, 0.0)
                    else -> maskValue(it)
                }
            }
        }
}
```

Aqui finalizamos a lógica de mascarar os dados. Agora, iremos criar uma classe que possa usar essa annotation e futuramente
ter seus dados sensíveis mascarados.

```kotlin
import domain.Mask

// Usamos a annotation como: @property:Mask para que possamos identificá-la na lógica anterior do método maskValue

data class Client(
    val name: String,
    @property:Mask
    val cpf: String,
    @property:Mask
    val address: Address
) {
    data class Address(
        @property:Mask
        val street: String,
        @property:Mask
        val number: String?,
        @property:Mask
        val postalCode: String
    )
}
```

Para testarmos isso, podemos criar um mock que nos ajude a validar alguns casos de teste.

```kotlin
import domain.client.Client
import domain.client.Client.Address

// Bloco de objetos mocados
object MaskTestFixture {

    fun client(addressNumber: String? = null) = Client(
        name = "Marlin Stone",
        cpf = "00000000000",
        address = Address(
            street = "Jr.Street",
            number = addressNumber,
            postalCode = "62375000"
        )
    )

    fun clientMasked(
        addressNumber: String? = null
    ) = client(addressNumber).let {
        it.copy(
            cpf = "*".repeat(it.cpf.length),
            address = Address(
                street = "*".repeat(it.address.street.length),
                number = it.address.number?.let {
                        number -> "*".repeat(number.length)
                },
                postalCode = "*".repeat(it.address.postalCode.length)
            )
        )
    }
}
```

```kotlin
import domain.MaskTestFixture.client
import domain.MaskTestFixture.clientMasked
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

// Validação dos casos de testes
class MaskTest: DescribeSpec({
    describe("Mask Annotation Test") {
        it ("Should mask the client's address") {
            val addressNumber = "123"
            val client = client(addressNumber)

            // Ira mascarar o cpf e todos os campos de address
            maskValue(client)

            client shouldBe clientMasked(addressNumber)
        }

        it ("Should not mask a null value") {
            val client = client()

            maskValue(client)

            client.address.number shouldBe null
        }
    }
})
```

