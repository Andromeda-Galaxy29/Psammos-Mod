Events.on(ContentInitEvent, e => {
    Vars.content.block("ferric-stone").attributes.set(Attribute.get("ferric-stone"), 1);
    Vars.content.block("ferric-craters").attributes.set(Attribute.get("ferric-stone"), 1);
    Vars.content.block("ferric-stone-wall").attributes.set(Attribute.get("ferric-stone"), 1);
});