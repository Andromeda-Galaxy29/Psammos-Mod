Attribute.add("ferric-stone");
Events.on(ContentInitEvent, e => {
    Vars.content.block("ferric-stone").attributes.set(Attribute.get("ferric-stone"), 0.11111111);
    Vars.content.block("ferric-craters").attributes.set(Attribute.get("ferric-stone"), 0.11111111);
});