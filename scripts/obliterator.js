Events.on(ContentInitEvent, e => {
    let ConsumeAny = new ConsumeItemFilter()
    ConsumeAny.filter = item => true
    Vars.content.block("psammos-Zz-obliterator").consume(ConsumeAny)
});