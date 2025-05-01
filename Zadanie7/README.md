✅ 3.0 Należy dodać litera do odpowiedniego kodu aplikacji serwerowej w
hookach gita https://github.com/MatiasM89/E-Biznes/commit/75243641a2debd80c0bfdbea8f8f4debeb95c96b
❌ 3.5 Należy wyeliminować wszystkie bugi w kodzie w Sonarze (kod
aplikacji serwerowej)
❌ 4.0 Należy wyeliminować wszystkie zapaszki w kodzie w Sonarze (kod
aplikacji serwerowej)
❌ 4.5 Należy wyeliminować wszystkie podatności oraz błędy bezpieczeństwa
w kodzie w Sonarze (kod aplikacji serwerowej)
❌ 5.0 Należy wyeliminować wszystkie błędy oraz zapaszki w kodzie
aplikacji klienckiej

What I also added but is not seen in commit due to being in .git/hooks is the code of pre-commit file
```
#!/bin/sh
cd Zadanie7
/home/matim/go/bin/golangci-lint run ./...
```

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=MatiasM89_E-Biznes&metric=bugs&token=6b64304159db0aa6056a85898c5454ecae2f2ce1)](https://sonarcloud.io/summary/new_code?id=MatiasM89_E-Biznes)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=MatiasM89_E-Biznes&metric=code_smells&token=6b64304159db0aa6056a85898c5454ecae2f2ce1)](https://sonarcloud.io/summary/new_code?id=MatiasM89_E-Biznes)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=MatiasM89_E-Biznes&metric=vulnerabilities&token=6b64304159db0aa6056a85898c5454ecae2f2ce1)](https://sonarcloud.io/summary/new_code?id=MatiasM89_E-Biznes)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=MatiasM89_E-Biznes&metric=security_rating&token=6b64304159db0aa6056a85898c5454ecae2f2ce1)](https://sonarcloud.io/summary/new_code?id=MatiasM89_E-Biznes)

