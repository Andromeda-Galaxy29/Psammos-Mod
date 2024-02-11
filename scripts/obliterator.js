const ConsumeNotBlast = class ConsumeNotBlast extends ConsumeItemFilter{
    constructor(){
        filter = item => item != Vars.content.item("blast-compound")
    }
}

Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-Zz-obliterator").consume(new ConsumeNotBlast())
});