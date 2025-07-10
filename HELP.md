# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.3/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.3/reference/web/servlet.html)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.3/reference/web/spring-security.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.



###StockService
Created an stockservice Application for getting sticks details

# 📘 API Documentation - Stock Market & Authentication API

**Base URL:** http://localhost:8080  
**Swagger UI:** http://localhost:8080/swagger-ui/index.html  
**OpenAPI JSON Spec:** http://localhost:8080/v3/api-docs  

---

## 🔐 Authentication APIs

### 🔸 Register User  
**POST** `/api/auth/register`  

### 🔸 Login User
 **POST**  '/api/auth/login'
 
## 🔐 Stocks APIs
**GET** /api/stocks/{symbol}

Replace symbol with "IBM"
Example : http://localhost:8080/api/stocks/IBM

**POST** /api/stocks/batch

Pass :
Request Body:
json : ["IBM", "AAPL"]
Example : http://localhost:8080/api/stocks/batch
 
