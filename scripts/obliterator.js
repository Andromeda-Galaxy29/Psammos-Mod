Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consume.add(new ConsumeItemFilter(Boolf(item => true)))
});