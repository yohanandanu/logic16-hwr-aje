package com.aje.logic16.app.GameLogic;

/**
 * Created by Arne on 23.04.14.
 */
public class Assignment
{
    public E_LITERAL_VALUE[] literals = new E_LITERAL_VALUE[GameLogic.NUM_LITERALS];

    public Assignment()
    {
        for (int i = 0; i < literals.length; i++)
        {
            literals[i] = E_LITERAL_VALUE.POSITIV;
        }
    }

    public Assignment(E_LITERAL_VALUE[] values)
    {
        System.arraycopy(literals, 0, values, 0, literals.length);
    }

    public Assignment(
            E_LITERAL_VALUE lit1,
            E_LITERAL_VALUE lit2,
            E_LITERAL_VALUE lit3,
            E_LITERAL_VALUE lit4,
            E_LITERAL_VALUE lit5,
            E_LITERAL_VALUE lit6,
            E_LITERAL_VALUE lit7,
            E_LITERAL_VALUE lit8
    )
    {
        literals[0] = lit1;
        literals[1] = lit2;
        literals[2] = lit3;
        literals[3] = lit4;
        literals[4] = lit5;
        literals[5] = lit6;
        literals[6] = lit7;
        literals[7] = lit8;
    }
}
