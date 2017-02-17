
import java.util.LinkedList;

public class EnvParser extends EnvParserot {

    public EnvParser(String prvNeterminal, LinkedList<Zeton> zetonte, int m,
            int n) {
        super(prvNeterminal, zetonte, m, n);
        terNeter();
        pravila();
        parseTable();
    }

    public void terNeter() {
        this.neTerminal("главна");
        this.neTerminal("останато");
        this.neTerminal("ѕид");
        this.neTerminal("жетон");

        this.terminal("околина");
        this.terminal("(");
        this.terminal("број");
        this.terminal(")");
        this.terminal("ѕидови");
        this.terminal("почеток");
        this.terminal("крај");
        this.terminal("жетони");
        this.terminal("робот");
        this.terminal("насока");
        this.terminal("правец");
        this.terminal("-");
        this.terminal(",");
    }

    public void pravila() {
        this.pravilata("околина останато");
        this.pravilata("( број , број ) ѕидови почеток ѕид крај жетони почеток жетон крај робот насока ( број , број )");
        this.pravilata("правец број број - број ѕид");
        this.pravilata(" ");
        this.pravilata("( број , број ) жетон");
        this.pravilata(" ");
    }

    public void parseTable() {
        this.tabela("главна", "околина", 1);
        this.tabela("останато", "(", 2);
        this.tabela("ѕид", "правец", 3);
        this.tabela("ѕид", "крај", 4);
        this.tabela("жетон", "(", 5);
        this.tabela("жетон", "крај", 6);
    }
}
