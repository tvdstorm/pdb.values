package org.eclipse.imp.pdb.facts;

public interface IWrapped extends IValue {
	/*
	 * "Marker" interface to allow external PDB values clients to 
	 * wrap object around IValues. This interface is (currently) used solely
	 * for unwrapping the IValue when computing equality of values.
	 */
	IValue getWrappedValue();
}
