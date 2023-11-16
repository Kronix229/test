Feature: Crear proyectos

  Scenario: Se crea un proyecto
    Given Se envian los parametros: nombre hola, descripcion proyecto y fecha 2023/10/10 14:30
    When Se intenta crear el proyecto
    Then El proyecto queda guardado con todos los datos correspondientes


  Scenario: La fecha se envia en formato incorrecto por lo que no se crea el proyecto
    Given Se envian los parametros: nombre hola, descripcion proyecto y fecha 10/10/2023 14:30
    When Se intenta crear el proyecto
    Then El proyecto no se crea y se lanza una excepción
