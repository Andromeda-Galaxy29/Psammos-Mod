Events.on(ContentInitEvent, e => {
    print(Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeItemFilter(item => true)))
});