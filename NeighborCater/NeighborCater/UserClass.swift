//
//  UserClass.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import Foundation
import UIKit
import Firebase

class User : NSObject {
    
    var userName : String
    var userEmail : String
    var userPhoneNumber : String
    
    init (snapshot:FIRDataSnapshot) {
        userName = snapshot.value!["name"] as! String
        userEmail = snapshot.value!["email"] as! String
        userPhoneNumber = snapshot.value!["number"] as! String
    }
}