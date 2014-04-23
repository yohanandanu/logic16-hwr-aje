package com.aje.logic16.app.GameLogic;

/**
 * Created by arne on 23.04.14.
 */
public class FormulaParser
{
    public FormulaParser()
    {}

    public void parseFormula(String formula, Conjunction[] conjunctions)
    {
        formula = formula.substring(1);
        String[] conjunctionStrings = formula.split("#");

        for (int i = 0; i < conjunctionStrings.length; i++)
        {
            parseConjunction(conjunctionStrings[i], conjunctions[i]);
        }
    }

    private void parseConjunction(String conjunctionString, Conjunction conjunction)
    {
        for (int literalIndex = 0; literalIndex < conjunctionString.length(); literalIndex++)
        {
            switch (conjunctionString.charAt(literalIndex))
            {
                case '+':
                    conjunction.getLiteral(literalIndex).setLiteral(E_LITERAL_VALUE.POSITIV);
                    break;
                case '-':
                    conjunction.getLiteral(literalIndex).setLiteral(E_LITERAL_VALUE.NEGATIV);
                    break;
                case '0':
                default:
                    conjunction.getLiteral(literalIndex).setLiteral(E_LITERAL_VALUE.NOT_SET);
                    break;
            }
        }
    }


}
