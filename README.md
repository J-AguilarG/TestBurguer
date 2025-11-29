# TestBurguer
# Test Técnico: API de Gestión de Hamburguesas

Este repositorio contiene dos implementaciones de una API RESTful para la gestión de un menú de hamburguesas (CRUD), desarrolladas como parte de la prueba técnica.

El objetivo es comparar la implementación entre dos tecnologías backend populares: **Python (Flask)** y **Java (Spring Boot)**.

## Estructura del Proyecto

* `/flask-api`: Contiene la implementación en Python con SQLite.
* `/spring-boot-api`: Contiene la implementación en Java con H2 Database.

---

## 1. Versión Python (Flask)

Esta versión utiliza **Flask** como framework y **SQLite** como base de datos embebida en fichero.

### Requisitos
* Python 3.x instalado.

### Cómo ejecutarlo

1.  Navega a la carpeta del proyecto:
    cd flask-api
    
2.  Instala las dependencias:
    pip install -r requirements.txt
    
3.  Inicia la aplicación:
    python app.py
    

La API estará disponible en: `http://localhost:5000/burgers`

---

## 2. Versión Java (Spring Boot)

Esta versión utiliza **Spring Boot 3** y **H2 Database** como base de datos.

### Requisitos
* Java 17 (o superior) instalado.
* Maven.

### Cómo ejecutarlo

1.  Navega a la carpeta del proyecto:
    cd spring-boot-api
    
2.  Ejecuta la aplicación:
    ./mvnw spring-boot:run
    

**Nota Importante:** La aplicación está configurada para correr en el **puerto 8081**.

La API estará disponible en: `http://localhost:8081/burgers`

---

## Documentación de la API (Endpoints)

Ambas versiones exponen los mismos endpoints para garantizar la consistencia.

| Método | Endpoint        | Descripción                  |
| :----- | :-------------- | :--------------------------- |
| `GET`  | `/burgers`      | Listar todas las hamburguesas|
| `GET`  | `/burgers/{id}` | Ver detalle de una burger    |
| `POST` | `/burgers`      | Crear una nueva burger       |
| `PUT`  | `/burgers/{id}` | Actualizar una burger        |
| `DELETE`| `/burgers/{id}`| Eliminar una burger          |

### Ejemplo de JSON para crear (POST):
```json
{
  "nom": "Cheese Bacon",
  "description": "Doble carne con queso y bacon crujiente",
  "prix": 12.50,
  "disponible": true
}