# Bancocuscatlan API
API RESTful desarrollada con **Spring Boot**, Prueba tecnica.

## Ejecutar la aplicación

El proyecto utiliza Docker para garantizar un entorno consistente.

1.  **Requisitos:** Tener instalado Docker.
2.  **Clonar el repositorio** y navegar a la carpeta raíz del proyecto.
3.  **Ejecutar:**
    ```bash
    docker-compose up --build
    ```
4.  La API estará disponible en `http://localhost:8080/`.

## Componentes levantados
* **App Service:** Contenedor de la API (Spring Boot).
* **Database:** Contenedor de PostgreSQL con persistencia en el volumen `postgres_data`.
* **Conectarse a base de datos:**
  * **cadena de coneccion:** jdbc:postgresql://localhost:5432/db_banco
  * **Usuario:** ANGEL
  * **Contraseña:** ANGEL1

## Documentación y Testing (Swagger)
La API se encuentra documentada a través de Swagger. En esta herramienta es posible visualizar todos los endpoints disponibles, así como la información requerida para su ejecución (parámetros y cuerpo de la petición).

**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Seguridad (API Key)
Por motivos de seguridad, todos los endpoints están protegidos. Para interactuar con los controladores desde Swagger o cualquier cliente HTTP, se utiliza el siguiente encabezado:

* **Header Name:** `X-API-KEY`
* **Value:** `BANCO-CUSCATLAN-2026-SECRET`

En swagger se debe adjuntar en cualquiera de los enpoits el value de X-API-KEY, esta se encuentra en la parte superior derecha de cada controlador con un icono de candado.

### Testing
Ejecucion de test 
```bash
    mvn test
```

---