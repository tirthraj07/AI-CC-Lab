## How to execute

Open Developer's Console and click on `File` -> `New` -> `Apex Class`

Then paste the `CurrencyConverter.apxc` code

Then save the code with `Ctrl + S`
  
Then press `Ctrl + E` and Enter the following text

```java
CurrencyConverter converter = new CurrencyConverter();
System.debug(converter.convertCurrency('INR','USD',10000));
System.debug(converter.convertCurrency('INR','Pound',10000));
System.debug(converter.convertCurrency('INR','Euro',10000));
System.debug(converter.convertCurrency('INR','Yen',10000));
```

Then check the `Open Logs` and Click `Execute`

Then click on `Debug Only`. You should get the output