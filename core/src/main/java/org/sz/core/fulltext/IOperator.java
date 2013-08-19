package org.sz.core.fulltext;

import org.apache.lucene.index.IndexWriter;

public abstract interface IOperator
{
  public abstract void addDocument(IndexWriter paramIndexWriter);

  public abstract String[] getFields();
}

