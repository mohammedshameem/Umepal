package com.qiito.umepal.holder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MenuProfileListItemHolder implements List<MenuProfileListItemHolder> {
	
	private EntryItem entryItem;
	private SectionItem sectionItem;
	private boolean isSection = false;
	
	public EntryItem getEntryItem() {
		return entryItem;
	}
	public void setEntryItem(EntryItem entryItem) {
		this.entryItem = entryItem;
	}
	public SectionItem getSectionItem() {
		return sectionItem;
	}
	public void setSectionItem(SectionItem sectionItem) {
		this.sectionItem = sectionItem;
	}
	public boolean isSection() {
		return isSection;
	}
	public void setSection(boolean isSection) {
		this.isSection = isSection;
	}
	@Override
	public void add(int location, MenuProfileListItemHolder object) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean add(MenuProfileListItemHolder object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int location,
			Collection<? extends MenuProfileListItemHolder> collection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(
			Collection<? extends MenuProfileListItemHolder> collection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public MenuProfileListItemHolder get(int location) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<MenuProfileListItemHolder> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int lastIndexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<MenuProfileListItemHolder> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<MenuProfileListItemHolder> listIterator(int location) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MenuProfileListItemHolder remove(int location) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> collection) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public MenuProfileListItemHolder set(int location,
			MenuProfileListItemHolder object) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<MenuProfileListItemHolder> subList(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

