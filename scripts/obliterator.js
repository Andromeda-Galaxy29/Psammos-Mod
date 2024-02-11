Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeItemFilter(Boolf(item => item.explosiveness > 0.2)))
});