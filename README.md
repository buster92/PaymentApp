# Payment App
Android app testing Mercado Libre API
- Kotlin + DataBinding + Navigation + ViewModel + Repository Later (Retrofit only)

### Installation
- Pull the project and add Mercado Libre public API key into $HOME/.gradle/gradle.properties file:
```
PAYMENT_APP_API_KEY=xxxxxxxxxxxxxxxxxxxxxxxxx
```

### Features
#### Simulates a payment flow with the following steps:
- Enter amount to pay
- Select payment method
- Select payment issuer (depending on previous step)
- Select installments provided
- Finish payment (mocked)

### Limitations
- Only translated to spanish

### TODOs
- Improve API error catching and messages displayed to user.
- Improve UI
- Add unit testing
- Add more languages
