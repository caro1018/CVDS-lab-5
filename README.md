## PARTE III. - HACIENDO UNA APLICACIÓN WEB DINÁMICA USANDO EL PATRÓN MVC
En este ejercicio, va a implementar una aplicación Web muy básica, haciendo uso de spring MVC.

Para esto usaremos la documentación oficial de Spring con que aprenderemos las funciones básicas de este framework https://spring.io/guides/gs/serving-web-content/

Seguimos el paso a paso para crear nuestra aplicación web:

![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/77d41949-55ba-4ab8-9602-815c4243651e)

creamos metodo get en clase controller

```java

    @RequestMapping("/user/{id}")
    //@ResponseBody
    private ModelAndView getUser(@PathVariable Integer id, Model model) {
        //String uri = "https://jsonplaceholder.typicode.com/users/1";
        String uri = "https://jsonplaceholder.typicode.com/users/"+ id;
        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject(uri, User.class);

        //---
        Address address2 = user.getAddress();
        Geo geo2 = address2.getGeo();
        Company company2 = user.getCompany();

        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", user);
        modelAndView.addObject("address", address2);
        modelAndView.addObject("geo", geo2);
        modelAndView.addObject("company", company2);
        //---

        System.out.println("User: " + user);

        System.out.println("Userid: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());

        Address address = user.getAddress();
        System.out.println("Address: "
                + address.getStreet() + ", "
                + address.getCity() + ", "
                + address.getZipcode()
        );

        Geo geo = address.getGeo();
        System.out.println("Geo Lat: "
                + geo.getLat() + ", Geo Lng: "
                + geo.getLng()
        );

        Company company = user.getCompany();
        System.out.println("Company: "
                + company.getName() + ", "
                + company.getCatchPhrase() + ", "
                + company.getBs()
        );

        //return "User detail page.";
        return modelAndView;
    }

```

```html
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User page</title>
    <link th:href="@{/css/style.css}" rel="stylesheet" />
</head>
<body>
<h1>User details</h1>
<div class = "main_container">
    <div class="row">
        <div class="left">User Id:</div>
        <div class="left" th:text="${user.id}"></div>
    </div>

    <div class="row">
        <div class="left">Name: </div>
        <div class="left" th:text="${user.name}"></div>
    </div>

    <div class="row">
        <div class="left">Email: </div>
        <div class="left" th:text="${user.email}"></div>
    </div>

    <div class="row">
        <div class="left">Address: </div>
        <div class="left"
             th:text="${address.street + ', ' + address.suite + ', ' + address.city + ', ' + address.zipcode}">
        </div>
    </div>

    <div class="row">
        <div class="left">Geo: </div>
        <div class="left" th:text="${geo.lat + ', ' + geo.lng}"></div>
    </div>

    <div class="row">
        <div class="left">Company: </div>
        <div class="left" th:text="${company.name + ', ' + company.bs}"></div>
    </div>
</div>
</body>
</html>

```

Después de terminar el aprendizaje analice:
- ¿Por qué MVC obtiene ese nombre? (puede apoyarse de https://www.javatpoint.com/spring-mvc-tutorial) 
- ¿Cuáles son las ventajas de usar MVC?
- ¿Qué diferencia tiene la estructura de directorios de este proyecto comparado con las de proyectos pasados (con solo maven y java EE)?
- ¿Qué anotaciones usaste y cuál es la diferencia entre ellas?
- Ahora, haz el request GET http://localhost:8080/greeting usando Postman, y revisa si el body de la respuesta es igual a alguno de los archivos del proyecto. Significa eso que es un recurso web dinámico o estático?

## PARTE IV. - APLICACIÓN MVC PARA CONSUMO DE SERVICIO RESTful
Usando la arquitectura MVC del punto anterior (el proyecto anterior), realice una aplicación simple qué permita navegar gráficamente sobre esta API
https://jsonplaceholder.typicode.com/todos/1, puede guiarse de tutoriales como https://medium.com/@nutanbhogendrasharma/consume-rest-api-in-spring-boot-web-application-354c404850f0

Basados en el proyecto anterior realizamos los siguientes ajustes

<p align="center">Metodo get en clase controller</p>

```java

@RequestMapping("/delectus")
    //@ResponseBody
    private ModelAndView getDelectus(Model model) {
        String uri = "https://jsonplaceholder.typicode.com/todos/1";
        RestTemplate restTemplate = new RestTemplate();

        Delectus delectus  = restTemplate.getForObject(uri, Delectus.class);

        //---
        ModelAndView modelAndView = new ModelAndView("delectus");
        modelAndView.addObject("delectus", delectus);
        //---

        System.out.println("User: " + delectus);

        System.out.println("Userid: " + delectus.getUserId());
        System.out.println("id: " + delectus.getId());
        System.out.println("title: " + delectus.getTitle());
        System.out.println("completed: " + delectus.getCompleted());

        //return "User detail page.";
        return modelAndView;
    }
```

<p align="center">Plantilla html</p>

```html

<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>User page</title>
    <link th:href="@{/css/style.css}" rel="stylesheet" />
</head>
<body>
<h1>User details</h1>
<div class = "main_container">
    <div class="row">
        <div class="left">User Id:</div>
        <div class="left" th:text="${delectus.userId}"></div>
    </div>
    <div class="row">
        <div class="left">Id:</div>
        <div class="left" th:text="${delectus.id}"></div>
    </div>
    <div class="row">
        <div class="left">Title:</div>
        <div class="left" th:text="${delectus.title}"></div>
    </div>

    <div class="row">
        <div class="left">Completed:</div>
        <div class="left" th:text="${delectus.completed}"></div>
    </div>

</div>
</body>
</html>

```
Resultado:
        
 ![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/e1279335-a8bb-4f39-bc6a-6155f982358c)


Luego de terminada esta parte responda:
- ¿Qué es RESTful?

## PARTE V. - APLICACIÓN MVC JUEGO
¡Llego la hora del reto! Teniendo las bases del uso del framework, cree una nueva ruta, por ejemplo `/guess`, y agrege formulario básico con un campo llamado "número" (guía de como crear formularios HTML https://www.w3schools.com/html/)
<p align="center">Nuevo formulario en ruta `/guess` </p>

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Adivina el número</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<h2>Adivina el número</h2>

<p>Prize: <span th:text="${guess.prize}"></span></p>
<p>Attempts: <span th:text="${guess.attempt}"></span></p>

<!--form action="#" th:action="@{/compare}" method="post"-->
<form th:action="@{/compare}" method="post">
    <label for="numero">Ingrese número del 1 al 10</label><br>
    <input type="number" id="numero" name="numero">
    <input type="submit" value="Submit">
</form>

</body>
</html>

```
<p align="center">Nuevo formulario en ruta `/youWin` </p>

```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>GANASTE</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<b><p th:text="|Felicitaciones ganaste un premio de $ ${prize}|"></b>
<form action="#" th:action="@{/reset}" method="post">
    <input type="submit" value="Reset">
</form>
</body>
</html>

```
Y vamos a implementar la lógica de nuestro juego:
1. Se trata de un juego en línea para adivinar un número, en el que el ganador, si acierta en la primera oportunidad, recibe $100.000. Luego, por cada intento fallido, el premio
se reduce en $10.000, como en los juegos de apuesta, es natural qué quede en saldos negativos.
2. El número a adivinar debe ser generado en cada intento y comparado con el número qué el usuario está insertando, es un número de 1 a 10.
3. Debe existir un botón de reset, qué permita al jugador iniciar de nuevo.
4. La capa de controlador debe procer el número del usuario mediante método `POST`.

<p align="center">Lógica de juego en nueva clase Guess</p>

```java
package com.example.servingwebcontent;
import java.util.Random;

public class Guess {

    private int number;
    private int attempt;
    private int testNumber;
    private int prize;
    private boolean win;

    public Guess() {

        Random rand = new Random();
        prize = 100000;
        number = rand.nextInt(10)+1;
    }

    public void newNumber()
    {
        Random rand = new Random();
        number = rand.nextInt(10)+1;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getNumber() {
        return number;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getPrize() {
        return prize;
    }

    public boolean isWin() {
        return win;
    }

    public void reset()
    {
        prize = 100000;
        attempt = 0;
        win = false;
    }

    public boolean compare()
    {
        return testNumber == number;
    }

    public void fail()
    {
        if(!this.compare())
        {
            prize -= 10000;
            attempt +=1;

        }
    }


    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }
}
```

<p align="center">Procesar número en clase Controller</p>

```java

//----JUEGO ADIVINAR NÚMERO----begin
    @GetMapping("/guess")
    public String greeting(Model model)
    {
        model.addAttribute("guess", guess);

        return "guess";
    }

    @PostMapping("/compare")
    public String greetingCompare(@RequestParam("numero") int numero, Model model)
    {
        guess.setTestNumber(numero);
        if(!guess.compare())
        {
            guess.fail();

            model.addAttribute("attempt", guess.getAttempt());
            model.addAttribute("prize", guess.getPrize());
            return "redirect:/guess";
        }
        else
        {
            guess.newNumber();

            model.addAttribute("prize", guess.getPrize());
            return "youWin";
        }
    }

    @PostMapping("/reset")
    public String greetingReset(Model model)
    {
        guess.reset();
        model.addAttribute("attempt", guess.getAttempt());
        model.addAttribute("prize", guess.getPrize());
        return "redirect:/guess";
    }

    @PostMapping("/youWin")
    public String greetingYouWin(Model model)
    {
        model.addAttribute("prize", guess.getPrize());
        return "youWin";
    }

```


Analice las siguientes situaciones:
- ¿Qué pasa si abro el sitio de juegos en dos navegadores difententes?
  - Si abrimos el juego en dos navegadores diferentes se estará jugando en la misma instancia del juego.  

| Edge      | Chrome      |
|------------|------------|  
|![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/efc26fc1-385c-433a-9e49-db1d9cfd5e30)|![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/ad4864b4-545c-43d2-9c27-73823c118395)|
|![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/d075c34d-cf78-4f05-8f3a-1c1a7bd65d4f)|![image](https://github.com/caro1018/CVDS-lab-5/assets/77819591/e2a70b84-0411-4cc7-beaa-902df921b57c)|


    
- Si quisiera qué a cada jugador le aparecieran independientemente sus respectivos saldos. ¿Qué habría que hacer?
