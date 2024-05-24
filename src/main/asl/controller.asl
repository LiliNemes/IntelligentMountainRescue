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
    .print("Agent Controller started.");
    +remainingVotes(0);
    +bestBid(infinity, none, none);
    .print("Initialization complete: remainingVotes(0), bestBid(infinity, none, none)").

// Handle receiving a new bid when it is better than the current best
+!processBid(BidValue, Rescuer, Injured) : remainingVotes(VotesLeft) & bestBid(CurrentBest, _, _) & BidValue < CurrentBest <-
    !updateBestBid(BidValue, Rescuer, Injured);
    !decreaseVotes.

// Handle receiving a new bid when it is not better than the current best
+!processBid(BidValue, Rescuer, Injured) : remainingVotes(VotesLeft) & bestBid(CurrentBest, _, _) & BidValue >= CurrentBest <-
    !decreaseVotes.

// Update the best bid
+!updateBestBid(BidValue, Rescuer, Injured) <-
    -bestBid(_, _, _);
    +bestBid(BidValue, Rescuer, Injured).

// Decrease the number of remaining votes
+!decreaseVotes : remainingVotes(VotesLeft) <-
    NewVotesLeft = VotesLeft - 1;
    -remainingVotes(VotesLeft);
    +remainingVotes(NewVotesLeft);
    !checkFinalizeVote.

// Check if finalization is needed
+!checkFinalizeVote : remainingVotes(0) <-
    !finalizeVoteWithBestBid.

+!checkFinalizeVote : remainingVotes(VotesLeft) & VotesLeft > 0 <-
    .print("Remaining votes: ", VotesLeft, ". Continuing bid processing.").

// Finalize the voting process with the best bid
+!finalizeVoteWithBestBid : bestBid(BestValue, BestRescuer, BestInjured) & BestValue \== infinity <-
    .send(env, achieve, allocateInjured(BestRescuer, BestInjured)).

// Handle the case when no valid bids are received
+!finalizeVoteWithBestBid : bestBid(BestValue, _, _) & BestValue == infinity <-
    .print("No valid bids received.").

// Percept handler for new bids
+percept(newBid(Injured, BidValue, Rescuer)) <-
    !processBid(BidValue, Rescuer, Injured).

// Percept handler for remaining votes
+percept(remaining(Votes)) <-
    -remainingVotes(_);
    +remainingVotes(Votes).

// Percept handler for the best cost
+percept(bestCost(Value)) <-
    -bestBid(_, _, _);
    +bestBid(Value, none, none).
