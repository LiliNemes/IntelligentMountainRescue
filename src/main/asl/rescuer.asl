// Agent Rescuer
// Ha kap egy newBid típusú beliefet (a szavazási algoritmus során kapja), akkor a paramétereket -a saját nevével együtt- továbbítsa a controller ágensnek, hogy az eldönthesse, hogy ki lesz az aktuális forduló győztese
// Praktikus leszednie a konkrét newBid beliefet magáról miközben kezeli annak érkezését, hogy az általa tárolt kép a világról ne legyen túlzsúfolt (hanem csak a neve legyen benne gyakorlatilag)
// A nevét nem kell tárolja mert azt beépítetten tárolja.
// a használandó beliefek/perceptionök nevei az Env osztályból kiolvashatók többségében (van amihez nem nyúl hozzá az Env)