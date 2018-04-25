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
Najprej seveda potrebujemo igralno ploščo s polji na katerem so lahko figure ali je pa to polje prazno.
To je shranjeno v atributu Stanje stanje, ki je matrika polj.

Potem potrebujemo enum Igralec, ki je ali eden imed igralcev ali pa eden od teh dveh zmagovalec (ker do neodločenega rezultata ne bo prišlo.

Če nek igralec z neko figuro v svojem koraku je, mora ostati na potezi in mora igrati točno s to figuro naprej, zato e v atributu Lokacija nujnost shranjena lokacija njegove figure. Če pa ima na voljo igrati z vsemi, pa je nujnost seveda null.

Lokacija ima samo final koordinate x in y (kje na igralni plošči).

Poteza (pot):
Poteza je sestavljena iz zaporedja Lokacij, je v bistvu neka pot po kateri lahko igralčeva figura igra in po možnosti je nasprotnikove figure.
2 različni Potezi iz ene in iste figure se seveda lahko nekje srečata, zato igralec ne igra cele Poteze na enkrat ampak naenkrat odigra neko figuro iz prve lokacije na drugo (in potem če se pot nadaljuje ostane na potezi in mora nujno igrati s figuro, s katero je začel).

Pravilo;
Igralec mora izbirati med tistimi potezami, ki pojejo največ možno nasprotnikovih figur, če več potez poje isto, lahko igralec izbira.
Ta pravilo me najprej prisili v to da, ko se nekaj odigra, je treba na novo izračunati maksimum, koliko jih lahko igralec na potezi poje. To je shranjeno v atributu int maks.
Ta maksimum se mi zdi najbolje računati rekurzivno.
Zdaj če bi izračunal samo maksimum, potem ko bom želel igrati z neko figuro, se bodo za to figuro ponovno morale izračunati Poteze, ki jih lahko naredi in ali so sploh dovolj dolge, da ta figura lahko igra (ali poje dovolj asprotnikovih figur). Zato, sem razmišljal, da če že grem rekurzivno računati maksimum, si lahko tudi shranim možne Poteze v atribut in ko želim nekaj odigrati, takoj vidim, če je premik iz prve lokacije na drugo sploh možen. V mislih sem imel tudi, da ko bo uporabnik kliknil na neko figuro, mu lahko izriše možne Poteze (poti) po katerih ta figura lahko gre (če poje maksimalno nasprotnikovih figur).

