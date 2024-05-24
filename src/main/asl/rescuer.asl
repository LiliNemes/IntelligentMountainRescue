// Agent Rescuer
// Ha kap egy newBid típusú beliefet (a szavazási algoritmus során kapja), akkor a paramétereket -a saját nevével együtt- továbbítsa a controller ágensnek, hogy az eldönthesse, hogy ki lesz az aktuális forduló győztese
// Praktikus leszednie a konkrét newBid beliefet magáról miközben kezeli annak érkezését, hogy az általa tárolt kép a világról ne legyen túlzsúfolt (hanem csak a neve legyen benne gyakorlatilag)
// A nevét nem kell tárolja mert azt beépítetten tárolja.
// a használandó beliefek/perceptionök nevei az Env osztályból kiolvashatók többségében (van amihez nem nyúl hozzá az Env)

+!start
<- .my_name(Name);
   .print("Rescuer agent started with name: ", Name);

// Képességek kezelése
+newBid(InjuredId, BidValue)
<- .my_name(Name);
   .print("Received newBid for Injured ", InjuredId, " with BidValue ", BidValue);
   !sendBidToController(InjuredId, BidValue);
   -+newBid(InjuredId, BidValue);

// Cél: a bid továbbítása a controller-nek
+!sendBidToController(InjuredId, BidValue)
<- .my_name(Name);
   .send(controller, tell, newBid(Name, InjuredId, BidValue));
   .print("Sent bid to controller: ", newBid(Name, InjuredId, BidValue));
