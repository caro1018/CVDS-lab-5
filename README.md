### PARTE IV. - APLICACIÓN MVC PARA CONSUMO DE SERVICIO RESTful
Usando la arquitectura MVC del punto anterior (el proyecto anterior), realice una aplicación simple qué permita navegar gráficamente sobre esta API
https://jsonplaceholder.typicode.com/todos/1, puede guiarse de tutoriales como https://medium.com/@nutanbhogendrasharma/consume-rest-api-in-spring-boot-web-application-354c404850f0



Luego de terminada esta parte responda:
- ¿Qué es RESTful?

### PARTE V. - APLICACIÓN MVC JUEGO
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
