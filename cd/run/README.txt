Tento adresar obsahuje sestavene JAR soubory pripravene ke spusteni.

Disponuje take skriptem jak pro unixove operacni systemy (disponujici interpretem Bash), tak pro Windows.

Oba skripty se spousteji nasledujicim zpusobem:

./runit.sh <MODUL> <NAZEV_TRIDY> <ARGUMENTY ...>
runit.bat  <MODUL> <NAZEV_TRIDY> <ARGUMENTY ...>

kde <MODUL> symbolizuje jeden z modulu aplikace (cfa, fa, fta, feda) a <NAZEV_TRIDY> je nazev spustitelne tridy v patricnem modulu. <ARGUMENTY ...> jsou pote argumenty samotneho programu. 

Seznam modulu a v nich se nachazejicich spustitelnych trid je:

Modul cfa: (bunecne fuzzy automaty)
	CFASimulatorApp - GUI simulace bunecneho fuzzy automatu
	ConfigGeneratorTool - generator konfiguraci bunecneho fuzzy automatu
	ConfigDisplayerApp - jednoduchy GUI zobrazovac konfiguraci
	ConfigNoiserTool - generator sumu
	ImageConfigConverter - konverter mezi PNG obrazky a soubory konfiguraci (a zpet)


Modul fa: (fuzzy automaty)
	AutomatonOfWordApp - k retezci vygeneruje automat jej rozpoznavajici
	DataPressureApp - provede metodu lisovani dat
	DeterminisationApp - zdeterminizuje uvedeny automat
	MinimisationApp - provede fuzzy minimalizaci uvedeneho automatu
	ComputeWordApp - provede vypocet zadaneho automatu se zadanym retezcem
	DeformAutomatonApp - provede stanovene deformace automatu
	HandwrittenTextGuiApp - GUI aplikace pro rozpoznavani rucne psaneho textu
	TyposCorrecterApp - aplikace pro opravy preklepu

Modul fta: (fuzzy tree automaty)
	TreeOnFTaRunner - spusti vypocet automatu se zadanym S-termem

Modul feda: (event driven fuzzy automaty)
	PowerConsumptionComputeApp - provede vypocet simulace spotreby el. energie
	RunAutomaton - provede vypocet automatu


Podrobnejsi popis jednotlivych modulu a trid se nachazi v textu prace nebo v dokumentaci ke kodu. Argumenty vyzadovane kazdym modulem jsou vyobrazeny pri spusteni s argumentem --help.

Pri nesrovnalostech je mozne dale pouzit prepinace --verbose a/nebo --debug (v tomto poradi) uvedene jako prvni z argumentu.

Ukazky pouziti:
./runit.sh cfa ConfigGeneratorTool --help
./runit.sh cfa ConfigGeneratorTool conf.timf 100 random
./runit.sh cfa CFASimulatorApp 100 conf.timf simple-blur
./runit.sh cfa ConfigNoiserTool --verbose conf.timf nois.timf salt-and-pepper 0.1

./runit.sh fa AutomatonOfWordApp ../src/fapapp/fuzzy-automata/test-data/handwritten/gen-word-0a.timf aut1.timf
./runit.sh fa DeformAutomatonApp  aut1.timf def1.timf replace degree 0.2 insert-one degree 0.1
./runit.sh fa HandwrittenTextGuiApp --tnorm product aut1.timf def1.timf

./runit.sh fta TreeOnFTaRunner ../src/fapapp/fuzzy-tree-automata/test-data/m-ary-complete-trees/fta-of-3-ary.df ../src/fapapp/fuzzy-tree-automata/test-data/m-ary-complete-trees/3-ary-complete-tree.df

./runit.sh feda PowerConsumptionComputeApp ../src/fapapp/event-driven-fuzzy-automata/test-data/powers/some.timf 


