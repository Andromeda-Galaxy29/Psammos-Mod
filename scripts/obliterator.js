Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeItemFilter(item => true))
});