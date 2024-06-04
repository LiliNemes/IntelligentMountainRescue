// Agent controller
// a szavazást értékeli ki
// egy beliefben el kell tárolja a hátralévő szavazatok számát, majd ezt a számot minden szavazattal csökkentenie kell
// ezen kívül el kell tárolja az aktuálisan legolcsóbban elérhető sérült azonosítóját, az elérésének a költségét és annak a rescuernek a nevét aki a legolcsóbban tudja elérni
// kell kezelnie, ha kap egy új szavazat értesítést, ezt kétféle képpen is, mi legyen akkor ha a szavazat értéke kisebb mint a tárolt legkisebb költség és mi legyen akkor ha nem ez van
// közben a hátralévő szavazatok számlálóját csökkentenie kell a beliefjében és ha az utolsó szavazat jön -ezt kezelni kell a korábbi két esetben is, tehát ha kisebb v nagyobb a szavazat értéke-,
// akkor meg kell hívnia az Env -ben megjatározott megfelelő ASL függvényt a nyertes rescuer és injured nevének/azonosítójának paraméterül adásával
// praktikus ellenőrizni, hogy egy rwscuer egy sérültre ne addhasson több szavazatot
// a használandó beliefek/perceptionök nevei az Env osztályból kiolvashatók többségében (van amihez nem nyúl hozzá az Env)

// Initialization
!start.

+!start <-
    +lock(free);
    .print("Agent Controller started.").

+!updateMin(Rescuer, Injured, Cost) : bestCost(C) & (C > Cost) & remaining(R) & (R > 1) & lock(free) <-
    .abolish(lock(_));
    +lock(busy);
    .print("New minimum cost: ", Cost, " instead of ", C, " for ", Rescuer, " to reach ", Injured, " with ", R-1, " votes left.");
    .abolish(bestCost(_));
    +bestCost(Cost);
    .abolish(minRescuer(_));
    +minRescuer(Rescuer);
    .abolish(minInjured(_));
    +minInjured(Injured);
    .abolish(remaining(_));
    +remaining(R-1);
    .abolish(lock(_));
    +lock(free).

+!updateMin(Rescuer, Injured, Cost) : bestCost(C) & (C <= Cost) & remaining(R) & (R > 1) & lock(free) <-
    .abolish(lock(_));
    +lock(busy);
    .print("New cost: ", Cost, " against ", C, " for ", Rescuer, " to reach ", Injured, " with ", R-1, " votes left.");
    .abolish(remaining(_));
    +remaining(R-1);
    .abolish(lock(_));
    +lock(free).

+!updateMin(Rescuer, Injured, Cost) : bestCost(C) & (C > Cost) & remaining(1) & lock(free) <-
    .abolish(lock(_));
    +lock(busy);
    .print("New minimum cost: ", Cost, " instead of ", C, " for ", Rescuer, " to reach ", Injured, " with 0 votes left.");
    .abolish(bestCost(_));
    +bestCost(Cost);
    .print("Last vote received.");
    .abolish(remaining(_));
    .abolish(bestCost(_));
    .abolish(minRescuer(_));
    .abolish(minInjured(_));
    optimize(Rescuer, Injured);
    .abolish(lock(_));
    +lock(free).

+!updateMin(Rescuer, Injured, Cost) : bestCost(C) & (C <= Cost) & remaining(1) & lock(free) & minRescuer(R) & minInjured(I) <-
    .abolish(lock(_));
    +lock(busy);
    .print("New cost: ", Cost, " against ", C, " for ", Rescuer, " to reach ", Injured, " with 0 votes left.");
    .print("Last vote received.");
    .abolish(remaining(_));
    .abolish(bestCost(_));
    .abolish(minRescuer(_));
    .abolish(minInjured(_));
    optimize(R, I);
    .abolish(lock(_));
    +lock(free).

+!updateMin(Rescuer, Injured, Cost) <-
    .print("Invalid update.");
    .send(Rescuer, achieve, retry(Rescuer, Injured, Cost)).