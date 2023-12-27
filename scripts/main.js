Events.on(ContentInitEvent, e => {
    Vars.content.block("ferric-stone").attributes.add(Attribute.get("ferric-stone"), parseFloat(1));
    Vars.content.block("ferric-craters").attributes.add(Attribute.get("ferric-stone"), parseFloat(1));
    Vars.content.block("ferric-stone-wall").attributes.add(Attribute.get("ferric-stone"), parseFloat(1));
});