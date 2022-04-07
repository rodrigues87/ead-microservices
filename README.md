
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

    OU

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @JsonIgnore ignora completamente o atributo, já JsonProperty oculta apenas leitura ou escrita ou leitura/escrita
    caso @JsonIgnore JsonProperty estejam juntos @JsonIgnore irá ignorar prevalecer
    
##
    @CrossOrigin(origins = "*", maxAge = 3600)

##Para não mostrar erros
    private static final long serialVersionUID = 1L;


##Copiando valores de atributo do userDto para userModel
    BeanUtils.copyProperties(userDto, userModel);

#REPOSITORY
##Verificando existencia 
    boolean existsByUsername(String username);
    @Modifying permite a execução de comandos de delete/update/create

#VALIDAÇÃO
    @NotBlank - Não permite valores vazios e nulos
    @NotNull - Não permite valores nulos 
    @NotEmpty - Não permite valores nulos e o tamanho tem q ser > 0
    @Email - Verifica se o texto digitado segue o padrão de email

#JSONVIEW 
    É UMA BIBLIOTECA PARA INTERCEPTAÇÃO DOS JSONS ENVIADOS E RECEBIDOS NO PROJETO
    PARA USÁ-LO É NECESSÁRIO CRIAR UMA INTERFASSE PARA MEDIAR AS RELAÇÕES DOS ATRIBUTOS COM O DTO
    EM SEGUIDA, É NECESSÁRIO ASSINAR (DECORAR) O ATRIBUTO COM A INTEFASSE ADEQUADA

##SET E LIST
    SET- 
        *NÃO É ORDENADO
        *NÃO PERMITE DUPLICATAS
        *AO CONSULTAR RETORNA APENAS A PRIMEIRA
    LIST- 
        *É ORDENADO
        *PERMITE DUPLICATAS
        *AO CONSULTAR RETORNA TODAS AS CONSULTAS

##RELACIONAMENTOS
    *É recomendado fazer um direcionamento bidirecional, apontando sempre o relatedBy=""
        *diminui o numero de consultas do hibernate
        *evita a criação de tabelas auxiliares desnecessárias

    QUANDO?
        @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
            *EAGER - Padrão por default, retorna o objeto ou lista automaticamente (carregamento ansioso)
            *LAZY - Apenas retorna caso seja solicitado (carregamento lento)
            é recomendado trazer os dados de forma lenta
    COMO?
        @Fetch(FetchMode.SUBSELECT) - Divide a consulta em 2 partes, objeto principal e lista
        @Fetch(FetchMode.SELECT) - Faz selects para cada entidade no banco
        @Fetch(FetchMode.JOIN) - Reune toda a consulta em um unico comando  e ignora o lazy caso esteja setado(cuidado)

    @OnDelete(action = OnDeleteAction.CASCADE)
        Delega ao banco de dados a reponsabilidade de deletar as entidades relacionadas

    

##OBSERVAÇÕES
    * Pude perceber que os dtos estao sendo criados com os mesmos 
    atributos da classe modelo,não seria melhor criar a classe 
    dto e na classe modelo herdar os atributos do dto?

    
    define a forma como o dado vai ser buscado
        select: 
        join:
        subselect:

##SERVICE
    AO DECORAR O MÉTODO COM @Transactional GARANTIMOS QUE O MÉTODO IRÁ RETORNAR CASO HAJA ALGUM PROBLEMA NA PERSISTENCIA
    É INTERESSANTE USAR NO MOMENTO DE DELETAR ENTIDADES COM RELACIONAMENTO UM PARA MUITOS


##TEMPO
    As aplicações spring boot trazem por default o padrão ISO 8601 UTC

##LOGS
    slf4j ---> log4j
    logBack sucessor do log4j, é a que o spring usa
    log4j 2 traz todas as melhorias do loback


### logging:
    TRACE > DEBUG > INFO > WARNING > ERROR