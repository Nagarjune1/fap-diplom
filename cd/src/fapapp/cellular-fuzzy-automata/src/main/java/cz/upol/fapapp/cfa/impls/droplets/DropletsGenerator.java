package cz.upol.fapapp.cfa.impls.droplets;

/**
 * Function generating droplets into given {@link DropletsConfiguration}.
 * 
 * @author martin
 *
 */
@FunctionalInterface
public interface DropletsGenerator {
	public void generate(DropletsConfiguration config);
}
