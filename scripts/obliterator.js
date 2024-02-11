const ConsumeAny = extend(ConsumeItemFilter, {filter: item => true})

Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeAny())
});