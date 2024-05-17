// Agent controller
// a szavazást értékeli ki
// egy beliefben el kell tárolja a hátralévő szavazatok számát, majd ezt a számot minden szavazattal csökkentenie kell
// ezen kívül el kell tárolja az aktuálisan legolcsóbban elérhető sérült azonosítóját, az elérésének a költségét és annak a rescuernek a nevét aki a legolcsóbban tudja elérni
// kell kezelnie, ha kap egy új szavazat értesítést, ezt kétféle képpen is, mi legyen akkor ha a szavazat értéke kisebb mint a tárolt legkisebb költség és mi legyen akkor ha nem ez van
// közben a hátralévő szavazatok számlálóját csökkentenie kell a beliefjében és ha az utolsó szavazat jön -ezt kezelni kell a korábbi két esetben is, tehát ha kisebb v nagyobb a szavazat értéke-,
// akkor meg kell hívnia az Env -ben megjatározott megfelelő ASL függvényt a nyertes rescuer és injured nevének/azonosítójának paraméterül adásával
// praktikus ellenőrizni, hogy egy rwscuer egy sérültre ne addhasson több szavazatot
// a használandó beliefek/perceptionök nevei az Env osztályból kiolvashatók többségében (van amihez nem nyúl hozzá az Env)