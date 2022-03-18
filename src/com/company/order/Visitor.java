package com.company.order;

public interface Visitor {
    void visit(Order order);
    
    // Can add more Order types to visit
}
