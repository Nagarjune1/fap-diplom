Aplikace fuzzy a pravdepodobnostnich automatu

Martin Jasek, dne 9. 5. 2018

Katedra Informatiky
Prirodovedecka fakulta
Univerzita Palackeho v Olomouci

=================================================

Toto CD obsahuje:
	- adresar doc s plnym textem prace
	- adresar src se zdrojovymi kody aplikace
	- adresar run se sestavenou aplikaci a spoustecimi skripty

=================================================

Vysazeni textu prace:

Pro vysazeni plneho textu prace staci zavolat 'make' v adresari doc. Make vygeneruje soubor fapa-text-ki.pdf. Prikaz 'make clean' pomaze soubory vygenerovane v prubehu sazby.

=================================================

Sestaveni aplikace:

Aplikace fapapp se sestavuje pomoci Apache Maven. Pro vygenerovani sestavenych JAR souboru slouzi prikaz 'mvn clean package' spusteny z adresare fappapp v adresari src. V adresarich target kazdeho modulu se pote vygeneruje patricny JAR soubor.

Dalsi informace a moznosti k sestaveni (napr. vygenerovani javadoc dokumentace nebo vynechani testu) jsou v souboru README.md v korenovem adresari aplikace.

=================================================

Spousteni aplikace:

Aplikaci (resp. programy v ni obsazene) je mozno spoustet pomoci Mavenu. Alternativne, lze pouzit skripty v adresari run. Popis spousteni je uveden v readme.txt souboru v tomto adresari.


=================================================

