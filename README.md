# Telefonní Účet - Kalkulátor

Tento projekt implementuje technický modul pro výpočet částky k úhradě za telefonní účet na základě výpisu volání. Modul implementuje rozhraní `TelephoneBillCalculator`, které umožňuje vypočítat náklady na volání na základě poskytnutého vstupního výpisu volání.

## Použití

### Vstupní formát výpisu volání

Vstupní výpis volání by měl být ve formátu CSV s následujícími poli:

- Telefonní číslo v normalizovaném tvaru obsahující pouze čísla (např. 420774567453)
- Začátek hovoru ve tvaru dd-MM-yyyy HH:mm:ss
- Konec hovoru ve tvaru dd-MM-yyyy HH:mm:ss

Příklad:
420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00

### Výstup

Metoda `calculate` rozhraní `TelephoneBillCalculator` vrátí částku k uhrazení spočtenou dle vstupního výpisu volání na základě následujících pravidel:

- Minutová sazba v intervalu <8:00:00,16:00:00) je zpoplatněna 1 Kč za každou započatou minutu. Mimo uvedený interval platí snížená sazba 0,50 Kč za každou započatou minutu. Pro každou minutu hovoru je pro stanovení sazby určující čas započetí dané minuty.
- Pro hovory delší, než pět minut platí pro každou další započatou minutu nad rámec prvních pěti minut snížená sazba 0,20 Kč bez ohledu na dobu kdy telefonní hovor probíhá.
- V rámci promo akce operátora dále platí, že hovory na nejčastěji volané číslo v rámci výpisu nebudou zpoplatněny. V případě, že by výpis obsahoval dvě nebo více takových čísel, zpoplatněny nebudou hovory na číslo s aritmeticky nejvyšší hodnotou.

## Jak Používat Modul

1. Stáhněte si zdrojové soubory projektu z repozitáře.
2. Otevřete projekt ve svém vývojovém prostředí (např. IntelliJ IDEA, Eclipse).
3. Implementujte rozhraní `TelephoneBillCalculator` podle specifikace.
4. Spusťte automatické testy, které ověří správnost vaší implementace.
5. Ujistěte se, že vaše implementace správně zpracovává vstupní výpis volání.
