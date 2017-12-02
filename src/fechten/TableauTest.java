package fechten;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableauTest
{

	Tableau tablo;

	Tunier t;

	public TableauTest()
	{
		tablo = new Tableau(10, 5);
		t = new Tunier();
		for (int i = 0; i < 10; i++)
		{
			t.melde(new Fechter("A", "A", "A"));
		}
	}

	@Test
	public void testKonstruktor()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int k = 0; k < 10; k++)
			{
				Ergebnis e = tablo.getErgebnis(t.getFechter().get(i),
				        t.getFechter().get(k));
				if (i == k)
				{
					assertEquals(Ergebniszustand.UNGUELTIG, e.getZustand());
				} else
				{
					assertEquals(Ergebniszustand.AUSSTEHEND, e.getZustand());
				}
			}
		}

		for (int i = 0; i < 10; i++)
		{
			assertEquals(0, tablo.getErhalten(t.getFechter().get(i)));
			assertEquals(0, tablo.getGewonnen(t.getFechter().get(i)));
			assertEquals(0, tablo.getGefochten(t.getFechter().get(i)));
			assertEquals(0, tablo.getGegeben(t.getFechter().get(i)));
		}
	}
	
	@Test
	public void testGefechtEintragen()
	{
		tablo.gefechtEintragen(t.getFechter().get(0), t.getFechter().get(1), 5, 3, true);

		tablo.gefechtEintragen(t.getFechter().get(2), t.getFechter().get(1), 5, 3, true);

		tablo.gefechtEintragen(t.getFechter().get(2), t.getFechter().get(0), 5, 3, true);
		
		assertEquals(2, tablo.getGefochten(t.getFechter().get(0)));
		assertEquals(1, tablo.getGewonnen(t.getFechter().get(0)));
		assertEquals(8, tablo.getErhalten(t.getFechter().get(0)));
		assertEquals(8, tablo.getGegeben(t.getFechter().get(0)));
		
		assertEquals(2, tablo.getGefochten(t.getFechter().get(1)));
		assertEquals(0, tablo.getGewonnen(t.getFechter().get(1)));
		assertEquals(10, tablo.getErhalten(t.getFechter().get(1)));
		assertEquals(6, tablo.getGegeben(t.getFechter().get(1)));
		
		assertEquals(2, tablo.getGefochten(t.getFechter().get(2)));
		assertEquals(2, tablo.getGewonnen(t.getFechter().get(2)));
		assertEquals(6, tablo.getErhalten(t.getFechter().get(2)));
		assertEquals(10, tablo.getGegeben(t.getFechter().get(2)));
	}

}
