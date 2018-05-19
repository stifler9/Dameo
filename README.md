# Dameo
Dameo game (similar to checkers)

https://en.wikipedia.org/wiki/Dameo

## Game rules:

* a man may move one step sideways, straight or diagonally forward;
* an unbroken line of men (of any length) may move one step forward along its line (if the cell ahead is empty);
* kings move like a queen in chess (any number of unobstructed spaces in any direction);
* capturing is always performed by jumping over 1 opponent on your path (if the cell ahead is empty); in that case opponent's figure is removed from board;
* white moves first, then moves alternate; a player may not pass (skip) a turn;
* if a man reaches the last row of the board (the row nearest his opponent) at the conclusion of a move, it promotes to king;
* jumping (capturing) is mandatory, including making multiple jumps if available. If there are jumping options, the player must always select the option that captures the maximum number of the opponent's pieces (men or kings); if two options capture the same number, the player may choose;
* a multi-jump can consist of single jumps having a combination of different directions;
* a player whose pieces all become captured, or is unable to make a legal move, loses the game.


## Pristop k programiranju igre:
Potrebujemo igralno ploščo s polji na katerem so lahko figure ali je pa to polje prazno.
To je shranjeno v atributu Stanje stanje, ki je matrika polj.

Potrebujemo enum IgralecIgre, ki je ali eden imed igralcev ali pa eden od teh dveh zmagovalec (ker do neodločenega rezultata ne bo prišlo).

Če nek igralec, ko je na potezi, s svojo figuro je' in ima možnost jesti naprej, mora ostati na potezi in mora igrati točno s to figuro naprej, zato je v atributu Lokacija nujnost shranjena lokacija njegove figure. Če pa ima na voljo igrati z vsemi, pa je nujnost seveda null.

Lokacija ima samo final koordinate x in y (kje na igralni plošči).

Poteza (pot):
Poteza je sestavljena iz zaporedja Lokacij, je v bistvu neka pot po kateri lahko igralčeva figura igra in po možnosti je nasprotnikove figure.
2 različni Potezi iz ene in iste figure se seveda lahko nekje srečata, zato igralec ne igra cele Poteze na enkrat ampak naenkrat odigra neko figuro iz prve lokacije na drugo (in potem če se pot nadaljuje ostane na potezi in mora nujno igrati s figuro, s katero je začel).
