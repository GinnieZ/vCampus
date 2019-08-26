package vc.db;

public abstract interface Model
{
  public abstract boolean insert(Object paramObject);
  
  public abstract boolean modify(Object paramObject);
  
  public abstract boolean delete(Object paramObject);
  
  public abstract Object search(Object paramObject);
}
