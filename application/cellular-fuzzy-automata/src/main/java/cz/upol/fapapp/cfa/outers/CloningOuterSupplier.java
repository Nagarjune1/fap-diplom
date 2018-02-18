package cz.upol.fapapp.cfa.outers;

import cz.upol.fapapp.cfa.automata.CellState;
import cz.upol.fapapp.cfa.conf.CFAConfiguration;

/**
 * Constan suplier such using following schema:
 * 
 * <pre>
 * a a_b_c_..._d_e_f f
 * a|a b c ... d e f|f
 * g|g h i ... j k l|l
 * .| ...  ...  ... |.
 * m|m n o ... p q r|r
 * s|s_t_u_..._v_w_x|x
 * s s t u ... v w x x
 * </pre>
 * 
 * @author martin
 *
 */
public class CloningOuterSupplier implements CFAOuterCellSupplier {

	public CloningOuterSupplier() {
	}

	@Override
	public CellState computeOuterCell(int i, int j, CFAConfiguration config) {
		int size = config.getSize();
		if (i < 0) {
			i = 0;
		}
		if (i >= size) {
			i = size - 1;
		}
		if (j < 0) {
			j = 0;
		}
		if (j >= size) {
			j = size - 1;
		}

		return config.getCell(i, j);
	}

}
