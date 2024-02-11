Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consumes.add(new ConsumeItemFilter(Boolf(item => true)))
});