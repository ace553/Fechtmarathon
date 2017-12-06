package fechten;

import static org.junit.Assert.*;

import org.junit.Test;

import service.TunierService;

public class TableauTest
{

	Tableau tableau;

	TunierService t;
	
	Tunier tunier;

	public TableauTest()
	{
		tunier = new Tunier();
		
		t = new TunierService(tunier);
		for (int i = 0; i < 10; i++)
		{
			Fechter f = new Fechter("A", "A", "A");
			f.setzteAnwesend(true);
			t.melde(f);
		}
		t.starte();
		tableau = tunier.getTableau();
	}

	@Test
	public void testKonstruktor()
	{
		for (int i = 0; i < 10; i++)
		{
			for (int k = 0; k < 10; k++)
			{
				Ergebnis e = tableau.getErgebnis(t.getTeilnehmer().get(i),
				        t.getTeilnehmer().get(k));
				if (i == k)
				{
					assertEquals(ErgebnisStatus.UNGUELTIG, e.getZustand());
				} else
				{
					assertEquals(ErgebnisStatus.AUSSTEHEND, e.getZustand());
				}
			}
		}

		for (int i = 0; i < 10; i++)
		{
			assertEquals(0, tableau.erhaltenProperty(i).get());
			assertEquals(0, tableau.gewonnenProperty(i).get());
			assertEquals(0, tableau.gefochtenProperty(i).get());
			assertEquals(0, tableau.gegebenProperty(i).get());
		}
	}
	
	@Test
	public void testGefechtEintragen()
	{
		tableau.gefechtEintragen(t.getTeilnehmer().get(0), t.getTeilnehmer().get(1), 5, 3, true);

		tableau.gefechtEintragen(t.getTeilnehmer().get(2), t.getTeilnehmer().get(1), 5, 3, true);

		tableau.gefechtEintragen(t.getTeilnehmer().get(2), t.getTeilnehmer().get(0), 5, 3, true);
		
		assertEquals(2, tableau.gefochtenProperty(0).get());
		assertEquals(1, tableau.gewonnenProperty(0).get());
		assertEquals(8, tableau.erhaltenProperty(0).get());
		assertEquals(8, tableau.gegebenProperty(0).get());
		assertEquals(0, tableau.indexProperty(0).get());
		
		assertEquals(2, tableau.gefochtenProperty(1).get());
		assertEquals(0, tableau.gewonnenProperty(1).get());
		assertEquals(10, tableau.erhaltenProperty(1).get());
		assertEquals(6, tableau.gegebenProperty(1).get());
		assertEquals(-4, tableau.indexProperty(1).get());
		
		assertEquals(2, tableau.gefochtenProperty(2).get());
		assertEquals(2, tableau.gewonnenProperty(2).get());
		assertEquals(6, tableau.erhaltenProperty(2).get());
		assertEquals(10, tableau.gegebenProperty(2).get());
		assertEquals(4, tableau.indexProperty(2).get());
	}

}
