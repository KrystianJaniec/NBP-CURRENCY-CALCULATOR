# NBP-CURRENCY-CALCULATOR

Application based on data about currency from polish bank website called: NBP Web API (http://api.nbp.pl). 
Every currency has in this API actual rate in reference to polish national value.
Application has two crucial modeules: currency converter (EUR, GBP, USD, PLN) and statistical information about selected currency from selected date to now. 
Currency statistics algorithm calculated minium (MIN), maximum (MAX), average (AVG) value and standard deviation (SD).
If NBP published todays new data,aplication shows actual course euro, dolar and pound  in zloty on the home page.

Technologies used in this project:
- Java 8
- Spring (Boot, Data, Web MVC, RestTemplate)
- Bootstrap
- Thymeleaf
- jQuery
- JUnit, Mockito, AssertJ
