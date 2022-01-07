
##REST
###Conjunto de padrões de boas práticas para uma api (Níveis de maturidade de Richardson)

    1. utilizar do protocolo http
    2. utilizar de recursos bem definidos
    3. utilizar os métodos corretos para get,post,put , delete
    4. utilizar os retornos corretos conforme a resposta, 200,300,400,500
    5. precisa implementar o link de detalhe do item

##RESTFULL
    Para ser considerado restfull, é necessário que api atenda a todos os requisitos definidos no padrão rest


##Salvando Objeto como string no banco
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

##Ocultanto os atributos com valores nulos

    @JsonInclude(JsonInclude.Include.NON_NULL)

##Ocultando atributos não desejáveis

    @JsonIgnore
    private String password;

##
    @CrossOrigin(origins = "*", maxAge = 3600)

##Para não mostrar erros
    private static final long serialVersionUID = 1L;


##Copiando valores de atributo do userDto para userModel
    BeanUtils.copyProperties(userDto, userModel);

#REPOSITORY
##Verificando existencia 
    boolean existsByUsername(String username);

##OBSERVAÇÕES
    * Pude perceber que os dtos estao sendo criados com os mesmos 
    atributos da classe modelo,não seria melhor criar a classe 
    dto e na classe modelo herdar os atributos do dto?

