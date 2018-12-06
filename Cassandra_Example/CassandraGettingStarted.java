package com.siva.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

public class CassandraGettingStarted {


  public static void main(String[] args) {


    Session session;



    QueryOptions qo = new QueryOptions().setConsistencyLevel(ConsistencyLevel.QUORUM);



    Cluster cluster = Cluster.builder().withQueryOptions(qo).addContactPoint("100.97.79.52")
        .withCredentials("cassandra", "cassandra")
        .withLoadBalancingPolicy(new TokenAwarePolicy(new RoundRobinPolicy())).build();


    session = cluster.connect("siva_test");

    Statement statement = new SimpleStatement(" select * from siva_test.employees;");
    System.out.printf("Default consistency level = %s\n", statement.getConsistencyLevel());
    statement.setConsistencyLevel(ConsistencyLevel.ONE);
    System.out.printf("New consistency level = %s\n", statement.getConsistencyLevel());
    ResultSet result1 = session.execute(statement);
    for (Row row : result1) {
      System.out.format("%s %s %s %s \n", String.valueOf(row.getInt("dept")), row.getString("city"),
          row.getString("name"), String.valueOf(row.getInt("id")));
    }



    cluster.close();
  }
}
