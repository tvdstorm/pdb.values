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
package org.eclipse.imp.pdb.facts.util;

import java.util.*;

public abstract class AbstractSpecialisedImmutableMap<K, V> extends AbstractImmutableMap<K, V> {
	@SuppressWarnings("rawtypes")
	private static ImmutableMap EMPTY_MAP = new Map0();

	@SuppressWarnings("unchecked")
	public static <K, V> ImmutableMap<K, V> mapOf() {
		return EMPTY_MAP;
	}
	
	public static <K, V> ImmutableMap<K, V> mapOf(K key1, V val1) {
		return new Map1<K, V>(key1, val1);
	}

	public static <K, V> ImmutableMap<K, V> mapOf(K key1, V val1, K key2, V val2) {
		return new Map2<K, V>(key1, val1, key2, val2);
	}

	public static <K, V> ImmutableMap<K, V> mapOf(K key1, V val1, K key2, V val2, K key3, V val3) {
		return new Map3<K, V>(key1, val1, key2, val2, key3, val3);
	}
	
	public static <K, V> ImmutableMap<K, V> mapOf(K key1, V val1, K key2, V val2, K key3, V val3, K key4, V val4) {
		return new ImmutableShareableHashMapWrapper<K, V>(key1, val1, key2, val2, key3, val3, key4, val4);
	}

	public static <K, V> ImmutableMap<K, V> mapOf(Map<K, V> map) {
		if (map instanceof AbstractSpecialisedImmutableMap) {
			return (ImmutableMap<K, V>) map;
		} else {
			return flattenMapFrom(map);
		}
	}
	
	@SafeVarargs
	protected static <K, V> ImmutableMap<K, V> flattenMapFrom(Map<K, V>... maps) {
		final ShareableHashMap<K, V> newContent = 
				new ShareableHashMap<>();
				
		for (Map<K, V> map : maps)
			newContent.putAll(map);
		
		return new ImmutableShareableHashMapWrapper<K, V>(newContent);
	}	
}

class Map0<K, V> extends AbstractSpecialisedImmutableMap<K, V> {
	Map0() {
	}

	@Override
	public boolean containsValue(Object val) {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public V get(Object key) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return Collections.emptySet();
	}

	@Override
	public ImmutableMap<K, V> __put(K key, V val) {
		return new Map1<K, V>(key, val);
	}

	@Override
	public ImmutableMap<K, V> __remove(K key) {
		return this;
	}

	@Override
	public ImmutableMap<K, V> __putAll(Map<K, V> map) {
		return flattenMapFrom(this, map);
	}
}

class Map1<K, V> extends AbstractSpecialisedImmutableMap<K, V> implements Map.Entry<K, V>, Cloneable {
	private final K key1;
	private final V val1;

	Map1(K key1, V val1) {
		this.key1 = key1;
		this.val1 = val1;
	}

	@Override
	public boolean containsValue(Object val) {
		if (val.equals(val1))
			return true;
		else
			return false;
	}

	@Override
	public boolean containsKey(Object key) {
		if (key.equals(key1))
			return true;
		else
			return false;
	}

	@Override
	public V get(Object key) {
		if (key.equals(key1))
			return val1;
		else
			return null;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public K getKey() {
		return key1;
	}

	@Override
	public V getValue() {
		return val1;
	}

	@Override
	public V setValue(V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return Collections.singleton((Entry<K, V>) this);
	}

	@Override
	public ImmutableMap<K, V> __put(K key, V val) {
		if (key.equals(key1))
			return mapOf(key, val);
		else
			return mapOf(key, val, key1, val1);
	}

	@Override
	public ImmutableMap<K, V> __remove(K key) {
		if (key.equals(key1))
			return mapOf();
		else
			return this;
	}
	
	@Override
	public ImmutableMap<K, V> __putAll(Map<K, V> map) {
		return flattenMapFrom(this, map);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Map2<K, V> extends AbstractSpecialisedImmutableMap<K, V> implements Cloneable {
	private final K key1;
	private final V val1;
	private final K key2;
	private final V val2;

	Map2(K key1, V val1, K key2, V val2) {
		this.key1 = key1;
		this.val1 = val1;
		this.key2 = key2;
		this.val2 = val2;
	}

	@Override
	public boolean containsValue(Object val) {
		if (val.equals(val1))
			return true;
		else if (val.equals(val2))
			return true;
		else
			return false;
	}

	@Override
	public boolean containsKey(Object key) {
		if (key.equals(key1))
			return true;
		else if (key.equals(key2))
			return true;
		else
			return false;
	}

	@Override
	public V get(Object key) {
		if (key.equals(key1))
			return val1;
		else if (key.equals(key2))
			return val2;
		else
			return null;
	}

	@Override
	public int size() {
		return 2;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new HashSet<Entry<K, V>>(
				Arrays.asList(
						new Map1<>(key1, val1),
						new Map1<>(key2, val2)));
	}

	@Override
	public ImmutableMap<K, V> __put(K key, V val) {
		if (key.equals(key1))
			return mapOf(key, val, key2, val2);
		else if (key.equals(key2))
			return mapOf(key1, val1, key, val);
		else
			return mapOf(key, val, key1, val1, key2, val2);
	}

	@Override
	public ImmutableMap<K, V> __remove(K key) {
		if (key.equals(key1))
			return mapOf(key2, val2);
		else if (key.equals(key2))
			return mapOf(key1, val1);
		else
			return this;
	}
	
	@Override
	public ImmutableMap<K, V> __putAll(Map<K, V> map) {
		return flattenMapFrom(this, map);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class Map3<K, V> extends AbstractSpecialisedImmutableMap<K, V> implements Cloneable {
	private final K key1;
	private final V val1;
	private final K key2;
	private final V val2;
	private final K key3;
	private final V val3;

	Map3(K key1, V val1, K key2, V val2, K key3, V val3) {
		this.key1 = key1;
		this.val1 = val1;
		this.key2 = key2;
		this.val2 = val2;
		this.key3 = key3;
		this.val3 = val3;
	}

	@Override
	public boolean containsValue(Object val) {
		if (val.equals(val1))
			return true;
		else if (val.equals(val2))
			return true;
		else if (val.equals(val3))
			return true;
		else
			return false;
	}

	@Override
	public boolean containsKey(Object key) {
		if (key.equals(key1))
			return true;
		else if (key.equals(key2))
			return true;
		else if (key.equals(key3))
			return true;
		else
			return false;
	}

	@Override
	public V get(Object key) {
		if (key.equals(key1))
			return val1;
		else if (key.equals(key2))
			return val2;
		else if (key.equals(key3))
			return val3;
		else
			return null;
	}

	@Override
	public int size() {
		return 3;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new HashSet<Entry<K, V>>(
				Arrays.asList(
						new Map1<>(key1, val1),
						new Map1<>(key2, val2),
						new Map1<>(key3, val3)));
	}

	@Override
	public ImmutableMap<K, V> __put(K key, V val) {
		if (key.equals(key1))
			return mapOf(key, val, key2, val2, key3, val3);
		else if (key.equals(key2))
			return mapOf(key1, val1, key, val, key3, val3);
		else if (key.equals(key3))
			return mapOf(key1, val1, key2, val2, key, val);
		else {
			return mapOf(key, val, key1, val1, key2, val2, key3, val3);
		}
	}

	@Override
	public ImmutableMap<K, V> __remove(K key) {
		if (key.equals(key1))
			return mapOf(key2, val2, key3, val3);
		else if (key.equals(key2))
			return mapOf(key1, val1, key3, val3);
		else if (key.equals(key3))
			return mapOf(key1, val1, key2, val2);
		else
			return this;
	}

	@Override
	public ImmutableMap<K, V> __putAll(Map<K, V> map) {
		return flattenMapFrom(this, map);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class ImmutableShareableHashMapWrapper<K, V> implements ImmutableMap<K, V> {

	private final ShareableHashMap<K, V> content;	
	
	ImmutableShareableHashMapWrapper(K key1, V val1, K key2, V val2, K key3, V val3, K key4, V val4) {
		this.content = new ShareableHashMap<>();
		
		this.content.put(key1, val1);
		this.content.put(key2, val2);
		this.content.put(key3, val3);
		this.content.put(key4, val4);
	}	
	
	ImmutableShareableHashMapWrapper(ShareableHashMap<K, V> content) {
		this.content = content;
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		 throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return content.size();
	}

	@Override
	public boolean isEmpty() {
		return content.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return content.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return content.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return content.get(key);
	}

	@Override
	public Set<K> keySet() {
		return content.keySet();
	}

	@Override
	public Collection<V> values() {
		return content.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return content.entrySet();
	}

	@Override
	public ImmutableMap<K, V> __put(K key, V value) {
		final ShareableHashMap<K, V> newContent = 
				new ShareableHashMap<>(content);
		
		newContent.put(key, value);
		
		return new ImmutableShareableHashMapWrapper<K, V>(newContent);		
	}

	@Override
	public ImmutableMap<K, V> __remove(K key) {
		final ShareableHashMap<K, V> newContent = 
				new ShareableHashMap<>(content);
		
		newContent.remove(key);
		
		return new ImmutableShareableHashMapWrapper<K, V>(newContent);		
	}
	
	@Override
	public ImmutableMap<K, V> __putAll(Map<K, V> map) {
		final ShareableHashMap<K, V> newContent = 
				new ShareableHashMap<>(content);
		
		newContent.putAll(map);
		
		return new ImmutableShareableHashMapWrapper<K, V>(newContent);		
	}
}