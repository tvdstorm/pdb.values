/*******************************************************************************
 * Copyright (c) 2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI
 *******************************************************************************/
package org.eclipse.imp.pdb.facts.impl;

import org.eclipse.imp.pdb.facts.IRelation;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.impl.func.SetFunctions;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.visitors.IValueVisitor;
import org.eclipse.imp.pdb.facts.visitors.VisitorException;

public abstract class AbstractSet extends Value implements ISet {

    public AbstractSet(Type collectionType) {
    	super(collectionType);
    }

    protected static TypeFactory getTypeFactory() {
        return TypeFactory.getInstance();
    }

    /*
     * TODO: get rid of code duplication (@see AbstractList.inferListOrRelType)
     */
    protected static Type inferSetOrRelType(final Type elementType, final Iterable<IValue> content) {
        final Type inferredElementType;
        final Type inferredCollectionType;

        // is collection empty?
        if (content.iterator().hasNext() == false) {
            inferredElementType = getTypeFactory().voidType();
        } else {
            inferredElementType = elementType;
        }

        // consists collection out of tuples?
        if (inferredElementType.isTupleType()) {
            inferredCollectionType = getTypeFactory().relTypeFromTuple(inferredElementType);
        } else {
            inferredCollectionType = getTypeFactory().setType(inferredElementType);
        }

        return inferredCollectionType;
    }

    protected abstract IValueFactory getValueFactory();

    @Override
    public Type getElementType() {
        return getType().getElementType();
    }

    @Override
    public ISet insert(IValue e) {
        return SetFunctions.insert(getValueFactory(), this, e);
    }

    @Override
    public ISet union(ISet that) {
        return SetFunctions.union(getValueFactory(), this, that);
    }

    @Override
    public ISet intersect(ISet that) {
        return SetFunctions.intersect(getValueFactory(), this, that);
    }

    @Override
    public ISet subtract(ISet that) {
        return SetFunctions.subtract(getValueFactory(), this, that);
    }

    @Override
    public ISet delete(IValue e) {
        return SetFunctions.delete(getValueFactory(), this, e);
    }

    @Override
    public IRelation product(ISet that) {
        return SetFunctions.product(getValueFactory(), this, that);
    }

    @Override
    public boolean isSubsetOf(ISet that) {
        return SetFunctions.isSubsetOf(getValueFactory(), this, that);
    }

    @Override
    public boolean isEqual(IValue other) {
        return SetFunctions.isEqual(getValueFactory(), this, other);
    }

    @Override
    public boolean equals(Object other) {
        return SetFunctions.equals(getValueFactory(), this, other);
    }

    @Override
    public <T> T accept(IValueVisitor<T> v) throws VisitorException {
//        if (getElementType().isTupleType()) {
//            return v.visitRelation(this);
//        } else {
//            return v.visitSet(this);
//        }
        return v.visitSet(this);
    }

}
