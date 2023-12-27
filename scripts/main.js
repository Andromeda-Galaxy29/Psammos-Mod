Attribute.add("ferric-stone");
Events.on(ContentInitEvent, e => {
    Vars.content.block("ferric-stone").attributes.set(Attribute.get("ferric-stone"), parseFloat(1));
    Vars.content.block("ferric-craters").attributes.set(Attribute.get("ferric-stone"), parseFloat(1));
});