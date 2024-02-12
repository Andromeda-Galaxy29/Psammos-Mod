Events.on(ContentInitEvent, e => {
    print("debug");
    Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeItemFlammable())
});