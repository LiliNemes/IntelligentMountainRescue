// Agent controller
// a szavazást értékeli ki
// egy beliefben el kell tárolja a hátralévő szavazatok számát, majd ezt a számot minden szavazattal csökkentenie kell
// ezen kívül el kell tárolja az aktuálisan legolcsóbban elérhető sérült azonosítóját, az elérésének a költségét és annak a rescuernek a nevét aki a legolcsóbban tudja elérni
// kell kezelnie, ha kap egy új szavazat értesítést, ezt kétféle képpen is, mi legyen akkor ha a szavazat értéke kisebb mint a tárolt legkisebb költség és mi legyen akkor ha nem ez van
// közben a hátralévő szavazatok számlálóját csökkentenie kell a beliefjében és ha az utolsó szavazat jön -ezt kezelni kell a korábbi két esetben is, tehát ha kisebb v nagyobb a szavazat értéke-,
// akkor meg kell hívnia az Env -ben megjatározott megfelelő ASL függvényt a nyertes rescuer és injured nevének/azonosítójának paraméterül adásával
// praktikus ellenőrizni, hogy egy rwscuer egy sérültre ne addhasson több szavazatot
// a használandó beliefek/perceptionök nevei az Env osztályból kiolvashatók többségében (van amihez nem nyúl hozzá az Env)

{
  "initial_beliefs": [
    "remainingVotes(0)",
    "bestInjured(none)",
    "bestCost(1000000)",
    "bestRescuer(none)",
    "rescuerBid([])"
  ],
  "initial_goals": [
    "manage_bids"
  ],
  "plans": [
    {
      "trigger": "+!manage_bids",
      "context": "percept(remaining(R))",
      "body": [
        "+remainingVotes(R)",
        {
          "while": "remainingVotes(N) & N > 0",
          "do": [
            "+!evaluate_bid"
          ]
        }
      ]
    },
    {
      "trigger": "+!evaluate_bid",
      "context": "percept(newBid(Injured, Bid, Rescuer))",
      "body": [
        "remainingVotes(N)",
        {
          "if": "N == 1",
          "then": [
            "+update_rescuerBid(Rescuer, Injured, Bid)",
            "+!finalize_bidding"
          ],
          "else": [
            "+update_rescuerBid(Rescuer, Injured, Bid)",
            "-remainingVotes(N)",
            "+remainingVotes(N-1)"
          ]
        }
      ]
    },
    {
      "trigger": "+!finalize_bidding",
      "context": "true",
      "body": [
        "+!calculate_min_avg"
      ]
    },
    {
      "trigger": "+!calculate_min_avg",
      "context": "true",
      "body": [
        "+find_min_avg(Rescuer, Injured, BestCost)",
        "+allocate(Rescuer, Injured)",
        "+bestInjured(Injured)",
        "+bestCost(BestCost)",
        "+bestRescuer(Rescuer)",
        "+!update_beliefs"
      ]
    },
    {
      "trigger": "+!update_beliefs",
      "context": "true",
      "body": [
        "+remainingVotes(0)",
        "+bestInjured(none)",
        "+bestCost(1000000)",
        "+bestRescuer(none)",
        "+rescuerBid([])"
      ]
    }
  ],
  "actions": [
    {
      "name": "update_rescuerBid",
      "params": ["Rescuer", "Injured", "Bid"],
      "body": [
        "rescuerBid(RB)",
        "append([(Rescuer, Injured, Bid)], RB, NewRB)",
        "-rescuerBid(RB)",
        "+rescuerBid(NewRB)"
      ]
    },
    {
      "name": "find_min_avg",
      "params": ["Rescuer", "Injured", "BestCost"],
      "body": [
        "rescuerBid(RB)",
        "calculate_average_cost(RB, AvgCosts)",
        "find_min(AvgCosts, (BestRescuer, BestAvg))",
        "find_min_bid(RB, BestRescuer, (BestInjured, BestBid))",
        "Rescuer = BestRescuer",
        "Injured = BestInjured",
        "BestCost = BestBid"
      ]
    },
    {
      "name": "allocate",
      "params": ["Rescuer", "Injured"],
      "body": [
        ".send(env, tell, allocate_injured(Rescuer, Injured))"
      ]
    }
  ]
}

