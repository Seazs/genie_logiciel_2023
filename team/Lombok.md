Présentation Lombok
======================

# 1. Introduction

Lombok est une librairie permettant de rajouter des annotations dans le code.

Une liste donnant toutes les annotations ainsi que leur utilisation est [accessible en ligne](https://projectlombok.org/features/).

# 2. Intérêt

L'intérêt de Lombok est d'améliorer la lisibilité du code. 
En effet, il est plus simple de lire ceci:

```java
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


public class example {

    @Getter(AccessLevel.PROTECTED)
    private final int id;
    
    @Getter
    @Setter
    private static String user,pass;
}
```

que sa version classique qui prend beaucoup plus de place:

```java


public class example {

    private final int id;
    private static String user, pass;

    protected int getId() {
        return id;
    }

    public static String getPass() {
        return pass;
    }

    public static String getUser() {
        return user;
    }

    public static void setPass(String pass) {
        example.pass = pass;
    }

    public static void setUser(String user) {
        example.user = user;
    }
}
```